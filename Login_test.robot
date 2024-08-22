*** Settings ***
Library    SeleniumLibrary

*** Variables ***
${URL}              http://localhost:4200/login  # URL of the login page
${BROWSER}          chrome                        # Browser to use
${USERNAME}         Hello                         # The username to test with
${PASSWORD}         houssin                       # The password to test with
${LOGIN_BUTTON}     //button[@type='submit']      # XPath or CSS selector of the login button
${USERNAME_FIELD}   //input[@id='username']       # XPath or CSS selector of the username input
${PASSWORD_FIELD}   //input[@id='password']       # XPath or CSS selector of the password input
${HOME_PAGE_URL}    http://localhost:4200/home    # URL of the home page

*** Test Cases ***
Test Successful Login
    [Documentation]    Test a successful login and check redirection to the home page
    Open Browser    ${URL}    ${BROWSER}
    Maximize Browser Window
    Input Text    ${USERNAME_FIELD}    ${USERNAME}
    Input Text    ${PASSWORD_FIELD}    ${PASSWORD}
    Click Element    ${LOGIN_BUTTON}
    Wait Until Location Is    ${HOME_PAGE_URL}    10s
    Close Browser
