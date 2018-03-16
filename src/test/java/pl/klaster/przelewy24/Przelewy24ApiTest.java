package pl.klaster.przelewy24;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.klaster.przelewy24.Model.ApiResponse;
import pl.klaster.przelewy24.Model.RegisterPaymentData;
import pl.klaster.przelewy24.Model.VerifyPaymentData;
import pl.klaster.support.http.Request;
import pl.klaster.support.http.Response;
import pl.klaster.support.http.SpyHttp;

import java.util.HashMap;

public class Przelewy24ApiTest {
    private SpyHttp httpClient;

    @Before
    public void before() {
        this.httpClient = new SpyHttp(new Response(200, new HashMap<>(), ""));
    }

    @Test
    public void itPerformHttpTestRequest() {
        Przelewy24Api api = new Przelewy24Api(
                new Przelewy24Properties("1234", "5678", "12345abcde"),
                httpClient
        );

        api.testConnection();
        Request r = httpClient.lastRequest;

        Assert.assertEquals(RequestRegistry.expectedTestConnectionParams(), r.getParams());
        Assert.assertEquals("https://sandbox.przelewy24.pl/testConnection", r.getUrl());
    }

    @Test
    public void itPerformHttpRegisterRequest() {
        Przelewy24Api api = new Przelewy24Api(
                new Przelewy24Properties("1234", "5678", "12345abcde"),
                httpClient
        );

        api.registerPayment(new RegisterPaymentData("sesion_id", 12300, "noreply.uek.dev.test@gmail.com", "", "", ""));
        Request r = httpClient.lastRequest;

        Assert.assertEquals(RequestRegistry.expectedRegisterPaymentParams(), r.getParams());
        Assert.assertEquals("https://sandbox.przelewy24.pl/trnRegister", r.getUrl());
    }

    @Test
    public void itAllowObtainPaymentUrlByToken() {
        Przelewy24Api api = new Przelewy24Api(
                new Przelewy24Properties("1234", "5678", "12345abcde"),
                httpClient
        );

        String url = api.getPaymentUrl("payment_token_1234");
        Assert.assertEquals("https://sandbox.przelewy24.pl/trnRequest/payment_token_1234", url);
    }

    @Test
    public void itFillApiResponseWithArguments() {
        Przelewy24Api api = new Przelewy24Api(
                new Przelewy24Properties("1234", "5678", "12345abcde"),
                new SpyHttp(new Response(200, new HashMap<>(), "error=0&token=ABC123"))
        );

        ApiResponse response = api.registerPayment(new RegisterPaymentData("sesion_id", 12300, "kanclerj@uek.krakow.pl", "", "", ""));

        Assert.assertEquals("0", response.getValue("error"));
        Assert.assertEquals("ABC123", response.getValue("token"));
    }

    @Test
    public void itDoNotThrowErrorWhenVerificationIsOk() {
        Przelewy24Api api = new Przelewy24Api(
                new Przelewy24Properties("1234", "5678", "12345abcde"),
                new SpyHttp(new Response(200, new HashMap<>(), "error=0&token=ABC123"))
        );

        ApiResponse response = api.verifyPayment(new VerifyPaymentData("sesion_id", "order_id", 12300));

        Assert.assertEquals("0", response.getValue("error"));
    }

    @Test
    public void itFailWhenFailedRequest() {
        Przelewy24Api api = new Przelewy24Api(
                new Przelewy24Properties("1234", "5678", "12345abcde"),
                new SpyHttp(new Response(400, new HashMap<>(), "error=0&token=ABC123"))
        );

        try {
            ApiResponse response = api.verifyPayment(new VerifyPaymentData("sesion_id", "order_id", 12300));
            Assert.fail("Should throw exception");
        } catch (InvalidRequestException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void itFailWhenContainsErrorInResponse() {
        Przelewy24Api api = new Przelewy24Api(
                new Przelewy24Properties("1234", "5678", "12345abcde"),
                new SpyHttp(new Response(200, new HashMap<>(), "error=1&errorMessage=msg&token=ABC123"))
        );

        try {
            ApiResponse response = api.verifyPayment(new VerifyPaymentData("sesion_id", "order_id", 12300));
            Assert.fail("Should throw exception");
        } catch (InvalidRequestException e) {
            Assert.assertTrue(true);
        }
    }
}
