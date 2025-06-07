import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { signupUser } from "../api/api";     // Axios POST /api/auth/signup
import "./Signup.css";

const tracks = [
  { value: "DSA_BATCH1", label: "Data Structures & Algorithms - B1 - 9:00 AM – 11:00 AM" },
  { value: "DSA_BATCH2", label: "Data Structures & Algorithms - B2 - 11:15 AM – 1:15 PM" },
  { value: "FSD", label: "Full Stack Eng. & System Design - B1 - 9:00 AM – 11:00 AM" },
  { value: "ETL", label: "ETL - Data Analyst - B1 - 9:00 AM – 11:00 AM" },
  { value: "POWERBI", label: "Data Analyst - Power BI - B1 - 11:15 AM – 1:15 PM" },
  { value: "ADV_DSA", label: "Adv. Data Structures & Algorithms - B1 - 9:00 AM – 11:00 AM" },
  { value: "REACT", label: "React Developer - B1 - 11:15 AM – 1:15 PM" },
  { value: "FSD_ADV_CPP", label: "Full Stack Engineering, Adv. C++ & System Design - B1 - 11:15 AM – 1:15 PM" }
];

export default function Signup() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: "",
    registrationNumber: "",
    email: "",
    password: "",
    track: tracks[0].value,
  });
  const [errorMsg, setErrorMsg] = useState("");

  const handleChange = (e) => {
    setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
  };

  // const handleSubmit = async (e) => {
  //   e.preventDefault();
  //   try {
  //     const response = await signupUser(formData);
  //     if (response.data.success) {
  //       navigate("/login");
  //     } else {
  //       setErrorMsg(response.data.message);
  //     }
  //   } catch (err) {
  //     setErrorMsg(err.response?.data?.message || "Signup failed");
  //   }
  // };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await signupUser(formData);
      console.log("Full response:", response); // Debug log
      
      // Correct success check (response.data.success)
      if (response.data?.success) {
        navigate("/login");
      } else {
        setErrorMsg(response.data?.message || "Signup failed (server error)");
      }
    } catch (err) {
      console.error("Signup error:", err.response?.data);
      setErrorMsg(
        err.response?.data?.message || 
        "Signup failed. Please try again."
      );
    }
  };

  

  return (
    <div className="signup-container">
      <h2>Signup</h2>
      <form className="signup-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="name">Name:</label>
          <input
            required
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            className="form-input"
          />
        </div>

        <div className="form-group">
          <label htmlFor="registrationNumber">Registration Number:</label>
          <input
            required
            type="text"
            id="registrationNumber"
            name="registrationNumber"
            value={formData.registrationNumber}
            onChange={handleChange}
            className="form-input"
          />
        </div>

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

        <div className="form-group">
          <label htmlFor="track">Track:</label>
          <select
            id="track"
            name="track"
            value={formData.track}
            onChange={handleChange}
            className="form-select"
          >
            {tracks.map(t => (
              <option key={t.value} value={t.value}>
                {t.label}
              </option>
            ))}
          </select>
        </div>

        {errorMsg && <p className="error-message">{errorMsg}</p>}

        <button type="submit" className="form-button">Sign Up</button>
      </form>

      <p className="redirect-text">
        Already have an account? <a href="/login">Login</a>
      </p>
    </div>
  );
}
