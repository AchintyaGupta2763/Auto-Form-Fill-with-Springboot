import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';

// Components
import Login from './components/Login';
import Signup from './components/Signup';
import LandingPage from './components/LandingPage';

// ——— Protected Route Wrapper ———
const ProtectedRoute = ({ children }) => {
  const { user, loading } = useAuth();

  if (loading) return <div>Loading...</div>;
  if (!user) return <Navigate to="/login" />;
  return children;
};

// ——— App Routes ———
const AppRoutes = () => {
  const { user } = useAuth();

  return (
    <Routes>
      <Route path="/login" element={user ? <Navigate to="/landing" /> : <Login />} />
      <Route path="/signup" element={user ? <Navigate to="/landing" /> : <Signup />} />
      <Route path="/landing" element={<ProtectedRoute><LandingPage /></ProtectedRoute>} />

      <Route path="/" element={<Navigate to={user ? "/landing" : "/signup"} />} />
    </Routes>
  );
};

// ——— Main App Component ———
const App = () => {
  return (
    <AuthProvider>
      <Router>
        <AppRoutes />
      </Router>
    </AuthProvider>
  );
};

export default App;
