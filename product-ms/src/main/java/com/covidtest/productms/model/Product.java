package com.covidtest.productms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entity used to map a Product in the database
 */
@Entity
public class Product {

    /**
     * Id of the product
     * Auto-generated primary key
     */
    @Id
    @GeneratedValue
    private Long id;

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
     * Get the product id
     *
     * @return The product id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the product id
     *
     * @param id The new product id
     */
    public void setId(Long id) {
        this.id = id;
    }

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
