import React from "react";
import './App.css';
import Navigation from './components/Navigation';
import Routes from './Routes';
import { ImageProvider } from './ImageContext';

function App() {
  return (
    <div className="App">
      <ImageProvider>
        <Navigation />
        <Routes />
      </ImageProvider>
    </div>
  );
}

export default App;
