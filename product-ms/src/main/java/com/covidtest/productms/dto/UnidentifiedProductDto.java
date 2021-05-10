package com.covidtest.productms.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class UnidentifiedProductDto {

    /**
     * Name of the product
     */
    private String name;

    /**
     * Validity Duration of the product
     */
    private int validityDuration;

    /**
     * Usage instructions of the product
     */
    private String useInstructions;

    /**
     * Get the product name
     *
     * @return The product name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the product name
     *
     * @param name The new product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the product validity duration
     *
     * @return The product validity duration
     */
    public int getValidityDuration() {
        return validityDuration;
    }

    /**
     * Set the product validity duration
     *
     * @param validityDuration The new product validity duration
     */
    public void setValidityDuration(int validityDuration) {
        this.validityDuration = validityDuration;
    }

    /**
     * Get the product usage instructions
     *
     * @return The product usage instructions
     */
    public String getUseInstructions() {
        return useInstructions;
    }

    /**
     * Set the product usage instructions
     *
     * @param useInstructions The new product usage instructions
     */
    public void setUseInstructions(String useInstructions) {
        this.useInstructions = useInstructions;
    }
}
