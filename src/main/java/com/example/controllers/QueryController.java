package com.example.controllers;

import com.example.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/query")

public class QueryController {

    private final QueryService queryService;

    @GetMapping("/1_1/{status_id}")
    public int query_1_1(@PathVariable UUID status_id) {
        return queryService.query_1_1(status_id);
    }

    @GetMapping("/1_2/{company_id}")
    public int query_1_2(@PathVariable UUID company_id) {
        return queryService.query_1_2(company_id);
    }

    @GetMapping("/1_3/{date}")
    public int query_1_3(@PathVariable("date") Date date) {
        return queryService.query_1_3(date);
    }

    @GetMapping("/1_4/{date}")
    public int query_1_4(@PathVariable("date") Date date) {
        return queryService.query_1_4(date);
    }

    @GetMapping("/2_1")
    public int query_2_1() {
        return queryService.query_2_1();
    }

    @GetMapping("/2_2")
    public int query_2_2() {
        return queryService.query_2_2();
    }

    @GetMapping("/2_3")
    public int query_2_3() {
        return queryService.query_2_3();
    }

    @GetMapping("/3")
    public int query_3() {
        return queryService.query_3();
    }

    @GetMapping("/4")
    public int query_4() {
        return queryService.query_4();
    }

    @GetMapping("/5/{branch_id}")
    public int query_5(@PathVariable UUID branch_id) {
        return queryService.query_5(branch_id);
    }

    @GetMapping("/6")
    public int query_6() {
        return queryService.query_6();
    }

    @GetMapping("/7/{company_id}")
    public int query_7(@PathVariable UUID company_id) {
        return queryService.query_7(company_id);
    }

    @GetMapping("/8/{company_id}")
    public List<Map<String, Object>> query_8(@PathVariable UUID company_id) {
        return queryService.query_8(company_id);
    }

    @GetMapping("/9")
    public int query_9() {
        return queryService.query_9();
    }

    @GetMapping("/10/{status_id}")
    public int query_10(@PathVariable UUID status_id) {
        return queryService.query_10(status_id);
    }

    @GetMapping("/11")
    public int query_11() {
        return queryService.query_11();
    }

    @GetMapping("/12")
    public Map<String, Integer> query_12() {
        return queryService.query_12();
    }

    @GetMapping("/13_1/{summ}")
    public int query_13_1(@PathVariable Integer summ) {
        return queryService.query_13_1(summ);
    }

    @GetMapping("/13_2/{value}")
    public int query_13_2(@PathVariable String value) {
        return queryService.query_13_2(value);
    }

    @GetMapping("/13_3/{date}")
    public int query_13_3(@PathVariable("date") Date date) {
        return queryService.query_13_3(date);
    }

    @GetMapping("/13_4/{value}")
    public int query_13_4(@PathVariable String value) {
        return queryService.query_13_4(value);
    }

    @GetMapping("/14_1/{firstname}")
    public int query_14_1(@PathVariable String firstname) {
        return queryService.query_14_1(firstname);
    }

    @GetMapping("/14_2")
    public int query_14_2() {
        return queryService.query_14_2();
    }

    @GetMapping("/14_3")
    public int query_14_3() {
        return queryService.query_14_3();
    }

    @GetMapping("/14_4")
    public int query_14_4() {
        return queryService.query_14_4();
    }

}
