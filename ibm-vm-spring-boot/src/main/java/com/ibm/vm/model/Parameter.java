package com.ibm.vm.model;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class Parameter {

    @NotNull
    @Min(10)
    private int amount;

    @Override
    public String toString() {
        return "request : {" +
                "amount=" + amount +
                '}';
    }
}

