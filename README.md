   HOW TO TEST THE APPLICATION!

   You should have a mongoDB (using v3.2.5) instaled in your drive and runing for this tests.
   A Servlet container like Apache Tomcat (using v8). with the application deployed.



    1-Scenario: user should register employee lateness for each day


    TEST  Scenario 1: Using a REST client for example postman send a POST request to the
    endpoint: http://localhost:8080/app/entry and add user:user and password:user
    as a Basic Authorization credentials, also add as a Header Content-Type:application/json
    and in the body a JSON like this:

    {
      "punctuality":"false",
      "date":"2016-05-03",
      "email":"someemployeemail@google.com"
    }

     the field "punctuality" should be false for register a employee with lateness
     or true for an on time employee.

     2-Scenario: admin should see an assistance report for a date range


     TEST Scenario 2: Using a REST client for example postman send a GET request to the
     endpoint: http://localhost:8080/app/search/since=2016-04-28&until=2016-04-28/someemployeemail@google.com
     and add user:admin and password:admin as a Basic Authorization credentials. The parameters "since"
     and "until" are the range dates to search and you should add the email of the employee at the end of
     the endpoint after "/". It should return a list of arrives status registered for that employee in that
     range of time.

     3-Scenario: notify employee lateness every day on scheduled configure time.


     TEST Scenario 3: Using a REST client for example postman execute the first "TEST Scenario 1" with
     "punctuality":"false" for an employee with lateness, the "date":"2016-05-03" should correspond to
     the actual current date and an email where you can check that the mail has been sending by the
     application. The scheduler will send the email in the time configured in the
     file: resources/config.properties
     email:"Please do not be late, it will be deducted from your paycheck! :)".

     4-Scenario: data purge.

     TEST Scenario 4: Using a REST client for example postman execute the first "1-Scenario TEST" with
     the "date":"2016-03-03" field that correspond to a date older than a month.
     The scheduler will delete the entry in the time configured in the
     file: resources/config.properties for the scheduler to run.



