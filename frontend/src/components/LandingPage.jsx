import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import "./LandingPage.css";

export default function LandingPage() {
  const navigate = useNavigate();
  const { user, logout } = useAuth();
  const [greeting, setGreeting] = useState("");

  useEffect(() => {
    if (!user) {
      navigate("/login");
      return;
    }
    setGreeting(`Welcome, ${user.name}!`);
  }, [user, navigate]);

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div className="landing-container">
      <h2 className="landing-title">{greeting}</h2>
      <div className="landing-info">
        <p><strong>Email:</strong> {user.email}</p>
        <p><strong>Track:</strong> {user.track}</p>
      </div>
      <button onClick={handleLogout} className="logout-button">
        Logout
      </button>
    </div>
  );
}
