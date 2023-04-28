package cart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ProductCreationRequest {

    @NotBlank
    private final String name;

    @NotNull
    @URL
    private final String image;

    @Positive
    private final Integer price;

    @JsonCreator
    public ProductCreationRequest(
            @JsonProperty(value = "name") final String name,
            @JsonProperty(value = "image") final String image,
            @JsonProperty(value = "price") final Integer price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}
