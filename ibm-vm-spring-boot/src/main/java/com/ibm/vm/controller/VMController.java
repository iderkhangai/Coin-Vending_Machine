package com.ibm.vm.controller;


import com.ibm.vm.model.ResponseMessage;
import com.ibm.vm.model.Parameter;
import com.ibm.vm.service.VMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@Validated
@RequestMapping(value = "/api/v1")
public class VMController {
    static Logger logger = LoggerFactory.getLogger(VMController.class);
    ResponseMessage res = new ResponseMessage();
//    @Autowired
    VMService vmService = new VMService();

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/vm")
    public ResponseMessage calculateChange(@Valid @RequestBody Parameter input) throws Exception {
        logger.debug("Input parameter[Amount]: " + input.toString());
//        vmService.findMinCoins(input.getAmount());
        res.setChanges((ArrayList<Integer>) vmService.findMinCoins(input.getAmount()));
        logger.info("***Response being responded***");
        return res;
    }

//    @CrossOrigin(origins = "*", allowedHeaders = "*")
//    @GetMapping("/get")
//    public String get() {
//        return "test";
//    }
}
