package com.b07group2.taamapp;

import android.text.Editable;
import android.widget.EditText;


import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AdminLoginFragmentTest {

    @Mock
    private TextInputLayout mockUsernameInput;

    @Mock
    private TextInputLayout mockPasswordInput;

    private AdminLoginFragment fragment;

    @Before
    public void setUp() {
        fragment = new AdminLoginFragment();

        when(mockUsernameInput.getEditText()).thenReturn(mock(EditText.class));
        when(mockPasswordInput.getEditText()).thenReturn(mock(EditText.class));

        when(mockUsernameInput.getEditText().getText()).thenReturn(mock(Editable.class));
        when(mockPasswordInput.getEditText().getText()).thenReturn(mock(Editable.class));

        fragment.usernameInput = mockUsernameInput;
        fragment.passwordInput = mockPasswordInput;
    }

    @Test
    public void testVerifyUsernameAndPassword_CorrectCredentials() {
        // Mock the EditText and set the text
        EditText mockUsernameEditText = mockUsernameInput.getEditText();
        EditText mockPasswordEditText = mockPasswordInput.getEditText();

        when(mockUsernameEditText.getText().toString()).thenReturn("admin");
        when(mockPasswordEditText.getText().toString()).thenReturn("pass");

        // Call the method
        boolean result = fragment.verifyUsernameAndPassword();

        // Verify the results
        assertTrue(result);
    }

    @Test
    public void testVerifyUsernameAndPassword_IncorrectUsername() {
        // Mock the EditText and set the text
        EditText mockUsernameEditText = mockUsernameInput.getEditText();
        EditText mockPasswordEditText = mockPasswordInput.getEditText();

        when(mockUsernameEditText.getText().toString()).thenReturn("wrongUsername");
        when(mockPasswordEditText.getText().toString()).thenReturn("pass");

        // Call the method
        boolean result = fragment.verifyUsernameAndPassword();

        // Verify the results
        assertFalse(result);

        // check if setError is getting called
        verify(mockUsernameInput).setError(" ");
        verify(mockPasswordInput).setError("Username or Password is Incorrect*");
    }

    @Test
    public void testVerifyUsernameAndPassword_IncorrectPassword() {
        // Mock the EditText and set the text
        EditText mockUsernameEditText = mockUsernameInput.getEditText();
        EditText mockPasswordEditText = mockPasswordInput.getEditText();

        when(mockUsernameEditText.getText().toString()).thenReturn("admin");
        when(mockPasswordEditText.getText().toString()).thenReturn("wrongPassword");

        // Call the method
        boolean result = fragment.verifyUsernameAndPassword();

        // Verify the results
        assertFalse(result);

        // check if setError is getting called
        verify(mockUsernameInput).setError(" ");
        verify(mockPasswordInput).setError("Username or Password is Incorrect*");
    }

    @Test
    public void testVerifyUsernameAndPassword_EmptyCredentials() {
        // Mock the EditText and set the text
        EditText mockUsernameEditText = mockUsernameInput.getEditText();
        EditText mockPasswordEditText = mockPasswordInput.getEditText();

        when(mockUsernameEditText.getText().toString()).thenReturn("");
        when(mockPasswordEditText.getText().toString()).thenReturn("");

        // Call the method
        boolean result = fragment.verifyUsernameAndPassword();

        // Verify the results
        assertFalse(result);

        // check if setError is getting called
        verify(mockUsernameInput).setError(" ");
        verify(mockPasswordInput).setError("Username or Password is Incorrect*");
    }
}
