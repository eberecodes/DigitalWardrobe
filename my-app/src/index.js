import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router } from 'react-router-dom';

import './index.css';
import Navigation from './components/Navigation';
import Routes from './Routes';

ReactDOM.render(
    <Router>
        <div className="App">
            <Navigation />
            <Routes />
        </div>
    </Router>,
    document.getElementById('root')
);


