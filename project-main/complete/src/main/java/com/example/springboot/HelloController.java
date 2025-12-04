package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/")
	public String index() {
		return 
        """
                <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Login Page</title>

                        <style>
                            body {
                                font-family: Arial, sans-serif;
                                background-color: #f0f2f5;
                                display: flex;
                                justify-content: center;
                                align-items: center;
                                height: 100vh;
                                margin: 0;
                            }

                            .login-container {
                                background-color: white;
                                padding: 2rem;
                                border-radius: 8px;
                                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                                width: 300px;
                                text-align: center;
                            }
                            h2 {
                                margin-bottom: 1.5rem;
                                color: #333;
                            }
                            .input-group {
                                margin-bottom: 1rem;
                                text-align: left;
                            }
                            label {
                                display: block;
                                margin-bottom: 0.5rem;
                                color: #555;
                            }
                            input[type="text"], input[type="password"] {
                                width: 100%;
                                padding: 0.75rem;
                                border: 1px solid #ddd;
                                border-radius: 4px;
                                box-sizing: border-box;
                            }
                            button {
                                width: 100%;
                                padding: 0.75rem;
                                border: none;
                                border-radius: 4px;
                                background-color: #007bff;
                                color: white;
                                font-size: 1rem;
                                cursor: pointer;
                                transition: background-color 0.2s;
                            }
                            button:hover {
                                background-color: #0056b3;
                            }
                            #message {
                                margin-top: 1rem;
                                font-weight: bold;
                            }
                            .success {
                                color: green;
                            }
                            .error {
                                color: red;
                            }
                        </style>

                    </head>
                    <body>

                        <div class="login-container">

                            <h2>Login</h2>

                            <form id="loginForm" onsubmit="handleLogin(); return false;">
                                <div class="input-group">
                                    <label for="username">Username</label>
                                    <input type="text" id="username" name="username" required>
                                </div>
                                <div class="input-group">
                                    <label for="password">Password</label>
                                    <input type="password" id="password" name="password" required>
                                </div>
                                <button type="submit">Log In</button>
                            </form>
                            <div id="message"></div>

                        </div>

                        <div class="signup-container">
                            <h2>Create Account</h2>

                            <form id="signupForm" onsubmit="handleSignup(); return false;">
                                <div class="input-group">
                                    <label for="email">Email Address</label>
                                    <input type="email" id="email" name="email" required placeholder="user@example.com">
                                </div>
                                
                                <div class="input-group">
                                    <label for="username">Username</label>
                                    <input type="text" id="username" name="username" required>
                                </div>

                                <div class="input-group">
                                    <label for="password">Password</label>
                                    <input type="password" id="password" name="password" required>
                                </div>
                                
                                <div class="input-group">
                                    <label for="confirmPassword">Confirm Password</label>
                                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                                </div>

                                <button type="submit">Sign Up</button>
                            </form>

                            <div id="message"></div>
                            
                            <a href="/" class="login-link">Already have an account? Log In</a>
                        </div>
                
                        <script>
                            // submission handler
                            function handleLogin() {
                                //change


                                import { createClient } from '@supabase/supabase-js'

                                const supabaseUrl = 'https://obqqtvzkiwaeyasskayd.supabase.co'
                                const supabaseKey = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9icXF0dnpraXdhZXlhc3NrYXlkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQ2OTE4MjQsImV4cCI6MjA4MDI2NzgyNH0.3Wjwvpn6gZSjYdC0-ixtMk-uu7pa5vBMlqp0r6oL40g'
                                const supabase = createClient(supabaseUrl, supabaseKey)

                                async function getData() {
                                    let { data, error } = await supabase
                                        .from('your_table_name')
                                        .select('*');
                                    if (error) {
                                        console.error('Error fetching data:', error);
                                    } else {
                                        console.log('Fetched data:', data);
                                    }
                                }

                                async function insertData() {
                                    const { data, error } = await supabase
                                        .from('your_table_name')
                                        .insert([{ column_name: 'value' }]);

                                    if (error) {
                                        console.error('Error inserting data:', error);
                                    } else {
                                        console.log('Data inserted:', data);
                                    }
                                }

                                async function signUp(email, password) {
                                    const { user, error } = await supabase.auth.signUp({
                                        email: email,
                                        password: password
                                    });

                                    if (error) {
                                        console.error('Sign-up error:', error);
                                    } else {
                                        console.log('User signed up:', user);
                                    }
                                }

                                const correctUsername = "admin";
                                const correctPassword = "password123";

                                //important
                                const enteredUsername = document.getElementById("username").value;
                                const enteredPassword = document.getElementById("password").value;

                                //access to message div
                                const messageElement = document.getElementById("message");

                                if (enteredUsername === correctUsername && enteredPassword === correctPassword) {
                                    //correct 
                                    messageElement.textContent = "Welcome, " + correctUsername + "!";
                                    messageElement.className = "success";
                                    
                                    //work on redirection later
                                    //window.location.href = "dashboard.html";
      
                                    window.location.href = "/dashboard.html";
                                } else {
                                    //wrong
                                    messageElement.textContent = "Incorrect username or password.";
                                    messageElement.className = "error";
                                }
                            }
                        </script>

                    </body>
                    </html>
               """;
	}

}
