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
                        <script src="https://cdn.jsdelivr.net/npm/@supabase/supabase-js@2"></script>
                        
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
                    
                            <!-- LOGIN FORM WRAPPER -->
                            <div id="login-wrapper">
                                <h2>Login</h2>
                                <form onsubmit="handleLogin(); return false;">
                                    <div class="input-group">
                                        <label for="login-username">Username</label>
                                        <input type="text" id="login-username" required>
                                    </div>
                                    <div class="input-group">
                                        <label for="login-password">Password</label>
                                        <input type="password" id="login-password" required>
                                    </div>
                                    <button type="submit">Log In</button>
                                </form>
                                
                                <div class="toggle-link" onclick="toggleForms()">
                                    Don't have an account? Sign Up
                                </div>
                            </div>

                            <!-- SIGNUP FORM WRAPPER (Hidden by default) -->
                            <div id="signup-wrapper" style="display: none;">
                                <h2>Sign Up</h2>
                                <form onsubmit="handleSignup(); return false;">
                                    <div class="input-group">
                                        <label for="signup-email">Email</label>
                                        <input type="email" id="signup-email" required>
                                    </div>
                                    <div class="input-group">
                                        <label for="signup-username">Username</label>
                                        <input type="text" id="signup-username" required>
                                    </div>
                                    <div class="input-group">
                                        <label for="signup-password">Password</label>
                                        <input type="password" id="signup-password" required>
                                    </div>
                                    <!-- Reusing your blue button style for consistency -->
                                    <button type="submit">Create Account</button>
                                </form>

                                <div class="toggle-link" onclick="toggleForms()">
                                    Back to Login
                                </div>
                            </div>

                            <div id="message"></div>

                        </div>

                       <script>
                    // 1. Initialize Supabase
                    const supabaseUrl = 'https://obqqtvzkiwaeyasskayd.supabase.co';
                    const supabaseKey = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9icXF0dnpraXdhZXlhc3NrYXlkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQ2OTE4MjQsImV4cCI6MjA4MDI2NzgyNH0.3Wjwvpn6gZSjYdC0-ixtMk-uu7pa5vBMlqp0r6oL40g';

                    try {
                        supabase = window.supabase.createClient(supabaseUrl, supabaseKey);
                    } catch (e) {
                        console.error("Supabase library not loaded. Check script tag.");
                        document.getElementById("message").innerText = "System Error: Supabase not loaded.";
                    }

                    // 2. Toggle Logic (Switches content inside the same box)
                    function toggleForms() {
                        const loginWrap = document.getElementById('login-wrapper');
                        const signupWrap = document.getElementById('signup-wrapper');
                        const msg = document.getElementById('message');

                        if (loginWrap.style.display === 'none') {
                            // Show Login
                            loginWrap.style.display = 'block';
                            signupWrap.style.display = 'none';
                        } else {
                            // Show Signup
                            loginWrap.style.display = 'none';
                            signupWrap.style.display = 'block';
                        }
                        // Clear message
                        msg.textContent = '';
                        msg.className = '';
                    }

                    // 3. Login Logic
                    async function handleLogin() {
                        const user = document.getElementById("login-username").value;
                        const pass = document.getElementById("login-password").value;
                        const messageElement = document.getElementById("message");

                        // Check Hardcoded Admin
                        if (user === "admin" && pass === "password123") {
                            messageElement.textContent = "Welcome, " + user + "!";
                            messageElement.className = "success";
                            setTimeout(() => window.location.href = "/dashboard.html", 1000);
                            return;
                        }

                        // Check Supabase
                        messageElement.textContent = "Verifying...";
                        messageElement.className = "";
                        
                        const { data, error } = await supabase.auth.signInWithPassword({
                            email: user, 
                            password: pass
                        });

                        if (error) {
                            messageElement.textContent = "Incorrect username or password.";
                            messageElement.className = "error";
                        } else {
                            messageElement.textContent = "Login Successful!";
                            messageElement.className = "success";
                            setTimeout(() => window.location.href = "/dashboard.html", 1000);
                        }
                    }

                    // 4. Signup Logic
                    async function handleSignup() {
                        const email = document.getElementById("signup-email").value;
                        const username = document.getElementById("signup-username").value;
                        const pass = document.getElementById("signup-password").value;
                        const messageElement = document.getElementById("message");

                        messageElement.textContent = "Creating account...";
                        messageElement.className = "";

                        // FIX 3: Removed nested function and added metadata
                        const { data, error } = await supabase.auth.signUp({
                            email: email,
                            password: pass,
                            options: {
                                data: {
                                    username: username // Save the username!
                                }
                            }
                        });

                        if (error) {
                            messageElement.textContent = 'Error: ' + error.message;
                            messageElement.className = "error";
                        } else {
                            // Check if email confirmation is required
                            if (data.session == null && data.user) {
                                messageElement.textContent = 'Success! Please check your email to confirm account.';
                                messageElement.className = "success";
                            } else {
                                messageElement.textContent = 'Account created successfully!';
                                messageElement.className = "success";
                            }
                        }
                    } 

      
                    
                </script>
                        
                    </body>
                    </html>
               """;
	}

}
