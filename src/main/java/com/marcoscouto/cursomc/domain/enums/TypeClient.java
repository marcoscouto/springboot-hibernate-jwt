package com.marcoscouto.cursomc.domain.enums;

public enum TypeClient {

    PHYSICAL_PERSON(1, "Physical Person"),
    LEGAL_PERSON(2, "Legal Person");

    private int code;
    private String description;

    private TypeClient(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static TypeClient toEnum(Integer code){
        if(code == null) return null;

        for (TypeClient x: TypeClient.values())
            if(code.equals(x.getCode())) return x;

            throw new IllegalArgumentException("Invalid id: " + code);
    }
}
