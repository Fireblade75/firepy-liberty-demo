package nl.firepy.examples.liberty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class CatDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private FurColor color;

    @NotEmpty
    @Min(0)
    @Max(25)
    private int age;

    public CatDto() {

    }

    public CatDto(String name, FurColor color, int age) {
        this.name = name;
        this.color = color;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public FurColor getColor() {
        return color;
    }
}
