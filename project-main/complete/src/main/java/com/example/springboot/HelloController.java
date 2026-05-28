package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
public class HelloController {

    @GetMapping("/dashboard")
    public ResponseEntity<String> dashboard() {
        try {
            String html = new String(getClass().getResourceAsStream("/static/dashboard.html").readAllBytes());
            return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(html);
        } catch (Exception e) {
            return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body("<h1>Error loading dashboard</h1>");
        }
    }

    @GetMapping("/lock-screen")
    public String lockScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            
            if (os.contains("mac")) {
                new ProcessBuilder("osascript", "-e",
                    "tell application \"System Events\" to keystroke \"q\" using {command down, control down}"
                ).start();
            } else if (os.contains("win")) {
                new ProcessBuilder("rundll32.exe", "user32.dll,LockWorkStation").start();
            } else {
                new ProcessBuilder("loginctl", "lock-session").start();
            }
            return "locked";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/notify-login")
    public String notifyLogin(@RequestParam String message, @RequestParam String title) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            //title = "Time Management";
            //message = "Login Successful!";

            if (os.contains("mac")) {
                new ProcessBuilder("osascript", "-e",
                    String.format("display notification \"%s\" with title \"%s\"", message, title)
                ).start();
            } else if (os.contains("win")) {
                String script = String.format(
                    "Add-Type -AssemblyName System.Windows.Forms; " +
                    "[System.Windows.Forms.MessageBox]::Show('%s', '%s')",
                    message.replace("'", "\"").replace("\n", " | "),
                    title.replace("'", "\"")
                );
                new ProcessBuilder("powershell", "-Command", script).start();
            } else {
                new ProcessBuilder("notify-send", title, message).start();
            }
            return "ok";
        } catch (Exception e) {
            return "error";
        }
    }

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
                    
                            <!-- id for login-wrapper -->
                            <div id="login-wrapper">
                                <h2>Login</h2>
                                <form onsubmit="handleLogin(); return false;">
                                    <div class="input-group">
                                        <label for="login-username">Email</label>
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

                            <!--sign up id -->
                            <div id="signup-wrapper" style="display: none;">
                                <h2>Sign Up</h2>
                                <form onsubmit="handleSignup(); return false;">
                                    <div class="input-group">
                                        <label for="signup-email">Email</label>
                                        <input type="text" id="signup-email" required>
                                    </div>
                                    <div class="input-group">
                                        <label for="signup-password">Password</label>
                                        <input type="password" id="signup-password" required>
                                    </div>
                
                                    <button type="submit">Create Account</button>
                                </form>

                                <div class="toggle-link" onclick="toggleForms()">
                                    Back to Login
                                </div>
                            </div>

                            <div id="message"></div>

                        </div>

                       <script>
                    // supabase url and apikey
                    const supabaseUrl = 'https://cwklklsfelkdughcsvjc.supabase.co';
                    const supabaseKey = 'sb_publishable_OREojXS750lxe4WxTPp0aw_mihcLs3m';

                    try {
                        supabase = window.supabase.createClient(supabaseUrl, supabaseKey);
                    } catch (e) {
                        document.getElementById("message").innerText = "System Error: Supabase not loaded";
                    }

                    // switch from login to signup
                    function toggleForms() {
                        const loginWrap = document.getElementById('login-wrapper');
                        const signupWrap = document.getElementById('signup-wrapper');
                        const msg = document.getElementById('message');

                        if (loginWrap.style.display === 'none') {
                            // login
                            loginWrap.style.display = 'block';
                            signupWrap.style.display = 'none';
                        } else {
                            // signup
                            loginWrap.style.display = 'none';
                            signupWrap.style.display = 'block';
                        }
                        // clear
                        msg.textContent = '';
                        msg.className = '';
                    }

                    // login handler
                    async function handleLogin() {
                        const user = document.getElementById("login-username").value;
                        const pass = document.getElementById("login-password").value;
                        const messageElement = document.getElementById("message");

                        // not needed (doesnt save)
                        if (user === "admin" && pass === "password123") {
                            messageElement.textContent = "Welcome, " + user;
                            messageElement.className = "success";

                            await fetch(`/notify-login?message=Login+Successful&title=Time+Management`);

                            setTimeout(() => window.location.href = "/dashboard", 1000);
                            //setTimeout(() => window.location.href = "/dashboard.html", 1000);
                            return;
                        }

                        messageElement.textContent = "Verifying...";
                        messageElement.className = "";
                        
                        // check supabase
                        const { data, error } = await supabase.auth.signInWithPassword({
                            email: user, 
                            password: pass
                        });

                        if (error) {
                            messageElement.textContent = "Incorrect username or password";
                            messageElement.className = "error";
                        } else {
                            localStorage.setItem('user_email', user);

                            messageElement.textContent = "Login Successful";
                            messageElement.className = "success";
                            
                            await fetch(`/notify-login?message=Login+Successful&title=Time+Management`);

                            setTimeout(() => window.location.href = "/dashboard", 1000);
                            //setTimeout(() => window.location.href = "/dashboard.html", 1000);
                        }
                    }

                    // signup handler
                    async function handleSignup() {
                        const email = document.getElementById("signup-email").value;
                        const pass = document.getElementById("signup-password").value;
                        const messageElement = document.getElementById("message");

                        messageElement.textContent = "Creating account...";
                        messageElement.className = "";

                        // save credentials using supabase
                        const { data, error } = await supabase.auth.signUp({
                            email: email,
                            password: pass,
                            options: {
                                data: {
                                    //username: username // NOT NEEDED
                                }
                            }
                        });

                        if (error) {
                            messageElement.textContent = 'Error: ' + error.message;
                            messageElement.className = "error";
                        } else {
                            // Check if email confirmation is required
                            if (data.session == null && data.user) {
                                messageElement.textContent = 'Success! Please check your email to confirm account';
                                messageElement.className = "success";
                            } else {
                                messageElement.textContent = 'Account created successfully';
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
