import React from 'react';
import { Navbar, Nav, Button } from 'react-bootstrap';
import { withRouter, Link } from 'react-router-dom';
import logo from './logo.png';
import Container from 'react-bootstrap/Container';
import history from '.././history';
import "./Navigation.css";

const Navigation = () => {
    return (
        <Navbar>
            <Container fluid>
                <Navbar.Brand href="/">
                    <img
                        src={logo}
                        alt="Logo"
                        className="logo-img"
                    />
                </Navbar.Brand>
                <Nav className="dropdown-container">
                    <Button className="dropdown-button">
                        <span className="menu-icon">&#9776;</span>
                    </Button>
                    <div className="dropdown-content">
                        <Link to="/" onClick={() => history.push('/')}>Home</Link>
                        <Link to="/AddItem" onClick={() => history.push('/AddItem')}>Add Item</Link>
                        <Link to="/Wardrobe" onClick={() => history.push('/Wardrobe')}>View Wardrobe</Link>
                    </div>
                </Nav>
            </Container>
        </Navbar>
    );
}

export default withRouter(Navigation);