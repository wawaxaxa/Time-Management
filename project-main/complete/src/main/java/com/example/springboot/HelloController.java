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

                        <script>
                            // submission handler
                            function handleLogin() {
                                //change
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
