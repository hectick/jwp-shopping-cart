package cart.controller;

import cart.dto.ProductCreationRequest;
import cart.dto.ProductModificationRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("상품 전체 목록을 조회하면 상태코드 200을 반환하는지 확인한다")
    @Test
    void getAdminTest() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("상품을 등록하면 상태코드 201을 반환하는지 확인한다")
    @Test
    void postProductsTest() {
        final ProductCreationRequest request = new ProductCreationRequest("pbo", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10000000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/admin/products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("상품을 등록할 때 잘못된 형식의 이름이 들어오면 상태코드 400을 반환하는지 확인한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void throwExceptionWhenInvalidNamePostProductsTest(final String nameInput) {
        final ProductCreationRequest request = new ProductCreationRequest(nameInput, "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10_000_000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/admin/products")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품을 등록할 때 잘못된 범위의 가격이 들어오면 상태코드 400을 반환하는지 확인한다")
    @ParameterizedTest
    @ValueSource(ints = {0, 10_000_001})
    void throwExceptionWhenInvalidPricePostProductsTest(final int priceInput) {
        final ProductCreationRequest request = new ProductCreationRequest("pobi", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", priceInput);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/admin/products")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품을 등록할 때 잘못된 형식의 이미지 경로가 들어오면 상태코드 400을 반환하는지 확인한다")
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "image"})
    void throwExceptionWhenInvalidPricePostProductsTest(final String imageInput) {
        final ProductCreationRequest request = new ProductCreationRequest("pobi", imageInput, 10_000_000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/admin/products")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품을 수정하면 상태코드 204를 반환하는지 확인한다")
    @Test
    void putProductsTest() {
        final ProductModificationRequest request = new ProductModificationRequest(1L, "pbo", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10000000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/admin/products/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("상품을 수정할 때 잘못된 형식의 이름이 들어오면 상태코드 400을 반환하는지 확인한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void throwExceptionWhenInvalidNamePutProductsTest(final String nameInput) {
        final ProductModificationRequest request = new ProductModificationRequest(1L, nameInput, "ㅎㅎ", 10_000_000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/admin/products/" + request.getId())
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품을 수정할 때 잘못된 범위의 가격이 들어오면 상태코드 400을 반환하는지 확인한다")
    @ParameterizedTest
    @ValueSource(ints = {0, 10_000_001})
    void throwExceptionWhenInvalidPricePutProductsTest(final int priceInput) {
        final ProductModificationRequest request = new ProductModificationRequest(1L, "pobi", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", priceInput);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/admin/products/" + request.getId())
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품을 수정할 때 잘못된 형식의 이미지 경로가 들어오면 상태코드 400을 반환하는지 확인한다")
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "image"})
    void throwExceptionWhenInvalidPricePutProductsTest(final String imageInput) {
        final ProductModificationRequest request = new ProductModificationRequest(1L, "pobi", imageInput, 10_000_000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/admin/products/" + request.getId())
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품을 삭제하면 상태코드 204를 반환하는지 확인한다")
    @Test
    void deleteProductsTest() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/admin/products/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
