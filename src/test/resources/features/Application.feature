
  Feature: E-commerce application http://automationpractice.com/index.php

    @SignUp
    Scenario: user/customer registration
      Given the new user is on the home page as given in property files
      When I click on the Sign in button
      And I fill my email address in create account section of the page
      And I click on the Create an Account button
      And I am taken to Create an Account page
      And I provide fill the necessary information
      And I click on the Register button
      Then my registration should be successful

    @Login
    Scenario: User/Customer Sign in
      Given I am a registered user
      And I navigate to the home page
      When I click on the Sign in button
      And I fill my Email Address on Already Registered block
      And I fill my Password on Already Registered block
      And I click on the Sign in button in Already Registered block
      Then My account page is opened
      And My username is shown in the header
      And I am able to see the logout button


      @Checkout
      Scenario: Checkout few clothes
        Given I am a registered user and I am able to login to the portal
        When I click Women button in the header
        And I click the product with name "Faded Short Sleeve T-shirts"
        And I click Add to cart
        And I click on Proceed to Checkout on the popup window
        And I click on Proceed to Checkout on the summary section
        And I click on Proceed to Checkout on the address section
        And I agree to the Terms of Service
        And I click on Proceed to Checkout on the shipping section
        And I click on Payment method as Pay By Bank Wire
        And I click on confirm my order
        Then Order confirmation page is opened
        And The order is complete
        And current page is the last step of ordering




