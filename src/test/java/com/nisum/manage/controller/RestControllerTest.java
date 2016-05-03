package com.nisum.manage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.manage.config.MvcConfig;
import com.nisum.manage.persistence.ArriveStatus;
import com.nisum.manage.service.ArriveStatusServices;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.mockito.Mockito.*;

/**
 * Created by dpinto on 25-04-2016.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes=MvcConfig.class)
public class RestControllerTest {

    ///////////////////////////////////////////////////////
    private RestController restController;


    private ArriveStatusServices asServicesMock;
    ////////////////////////////////////////////////////////

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }



    @Before
    public void setUp() throws Exception{

        restController=new RestController();

        asServicesMock=mock(ArriveStatusServices.class);
        ReflectionTestUtils.setField(restController,"asServices",asServicesMock);

    }

    @After
    public void tearDown() {

    }

    @Test
    public void shouldAddNewArriveStatus() throws Exception {

        ArriveStatus as=getArriveStatus();

        UriComponentsBuilder ucBuilder=UriComponentsBuilder.newInstance();

        HttpHeaders headers = new HttpHeaders();

        Mockito.doNothing().when(asServicesMock).save(as);

        ResponseEntity<Void> expResult=new ResponseEntity<Void>(headers, HttpStatus.CREATED);

        ResponseEntity<Void> result = restController.insertArriveStatus(as, ucBuilder);

        verify(asServicesMock, times(1)).save(as);

        verifyNoMoreInteractions(asServicesMock);

        Assert.assertEquals(result, expResult);

    }

    @Test
    public void shouldReturnAListOfAllArriveStatusByDateRangeAndEmployeeEmail() throws Exception{

        String since="2014-06-05";
        String until="2014-06-20";
        String email="otro@gmail.com";

        ArriveStatus[] arr={getArriveStatus(),getArriveStatus()};

        ResponseEntity<List<ArriveStatus>> expResult=new ResponseEntity<List<ArriveStatus>>(Arrays.asList(arr), HttpStatus.OK);

        when(asServicesMock.findAll(since,until,email)).thenReturn(Arrays.asList(arr));

        ResponseEntity<List<ArriveStatus>> result = restController.listAllArriveStatus(since,until,email);

        verify(asServicesMock, times(1)).findAll(since,until,email);

        verifyNoMoreInteractions(asServicesMock);

        Assert.assertEquals(result, expResult);

    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ArriveStatus getArriveStatus() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        dateFormat.setLenient(false);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date= null;


        try {

            date = dateFormat.parse("2014-06-05");

        } catch (ParseException e) {

            e.printStackTrace();
        }

        ArriveStatus as = new ArriveStatus();
        as.setPunctuality(false);
        as.setEmail("otro@gmail.com");
        as.setDate(date);
        return as;
    }
}
