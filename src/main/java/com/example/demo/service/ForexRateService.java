package com.example.demo.service;

import com.example.demo.crud.ForexRateRepository;
import com.example.demo.vo.ForexRequest;
import com.example.demo.vo.ForexResponse;
import com.example.demo.entity.ForexRateEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ForexRateService {

    @Autowired
    private ForexRateRepository forexRateRepository;

    /**
     * 取得外匯成交資料，並將每日的美元/台幣欄位(USD/NTD)資料與日期(yyyy-MM-dd HH:mm:ss) insert 至 table
     *
     */
    public void fetchAndSaveForexRates() {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates";

        try {
            String response = restTemplate.getForObject(apiUrl, String.class);

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Map<String, String>>>() {}.getType();
            List<Map<String, String>> rates = gson.fromJson(response, listType);

            for (Map<String, String> rate : rates) {
                String dateStr = rate.get("Date");
                String usdToNtdStr = rate.get("USD/NTD");

                if (dateStr != null && usdToNtdStr != null) {
                    LocalDateTime exchangeDate = LocalDateTime.parse(dateStr + " 18:00:00", DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss"));
                    BigDecimal usdToNtd = new BigDecimal(usdToNtdStr);

                    ForexRateEntity forexRate = new ForexRateEntity();
                    forexRate.setUsdToNtd(usdToNtd);
                    forexRate.setDate(exchangeDate);

                    forexRateRepository.save(forexRate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 檢核傳入內容.
     *
     * @param request 傳入內容.
     * @return 檢核結果字串.
     */
    public String validateRequest(ForexRequest request) {
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        LocalDate yesterday = LocalDate.now().minusDays(1);

        if (!"usd".equalsIgnoreCase(request.getCurrency())) {
            return "目前僅支援美元查詢";
        }

        if (startDate.isBefore(oneYearAgo) || endDate.isAfter(yesterday) || startDate.isAfter(endDate)) {
            return "日期區間不符";
        }
        return null;
    }

    /**
     * 取得日期區間內的外匯資料.
     *
     * @param startDate 起始時間.
     * @param endDate 結束時間.
     * @return 筆數物件.
     */
    public List<ForexResponse> getForexRates(LocalDate startDate, LocalDate endDate) {

        List<ForexRateEntity> rates = forexRateRepository.findAll();
        return rates.stream()
                .filter(rate -> !rate.getDate().toLocalDate().isBefore(startDate) && !rate.getDate().toLocalDate().isAfter(endDate))
                .map(rate -> new ForexResponse(rate.getDate().toLocalDate(), rate.getUsdToNtd()))
                .collect(Collectors.toList());
    }
}
