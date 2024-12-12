package com.example.demo.controller;

import com.example.demo.vo.ErrorResponse;
import com.example.demo.vo.ForexRequest;
import com.example.demo.vo.ForexResponse;
import com.example.demo.vo.SuccessResponse;
import com.example.demo.service.ForexRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/forex")
public class ForexController {

    @Autowired
    private ForexRateService forexRateService;

    /**
     * 每日排程 18:00取得美元/台幣外匯成交資料.
     *
     */
    @GetMapping("/getExchangeData")
    public void runBatch() {
        forexRateService.fetchAndSaveForexRates();
    }

    /**
     * 取出日期區間內美元/台幣的歷史資料.
     * @param request 傳入內容.
     * @return ResponseEntity<?>
     */
    @PostMapping("/getForexRatesData")
    public ResponseEntity<?> getForexRates(@RequestBody ForexRequest request) {
        String validationError = forexRateService.validateRequest(request);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("E001", validationError));
        }

        List<ForexResponse> rates = forexRateService.getForexRates(request.getStartDate(), request.getEndDate());
        return ResponseEntity.ok(new SuccessResponse("0000", "成功", rates));
    }
}
