import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../api/api";       // Axios POST /api/auth/login
import { useAuth } from "../context/AuthContext";
import "./Login.css";

export default function Login() {
  const navigate = useNavigate();
  const { login } = useAuth();

  const [formData, setFormData] = useState({ email: "", password: "" });
  const [errorMsg, setErrorMsg] = useState("");

  const handleChange = (e) => {
    setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // loginUser returns response.data, so no need for response.data.success
      const response = await loginUser(formData);
      if (response.success) {
        const { token, user: userData } = response;
        login(userData, token);
        navigate("/landing");
      } else {
        setErrorMsg(response.message || "Invalid credentials");
      }
    } catch (err) {
      console.error("Login error:", err);
      setErrorMsg(err.response?.data?.message || "Login failed");
    }
  };

  return (
    <div className="login-container">
      <h2>Login</h2>
      <form className="login-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="email">SRM Email:</label>
          <input
            required
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            className="form-input"
          />
        </div>

        <div className="form-group">
          <label htmlFor="password">Password:</label>
          <input
            required
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            className="form-input"
          />
        </div>

        {errorMsg && <p className="error-message">{errorMsg}</p>}

        <button type="submit" className="form-button">Log In</button>
      </form>

      <p className="redirect-text">
        Don't have an account? <a href="/signup">Sign up</a>
      </p>
    </div>
  );
}