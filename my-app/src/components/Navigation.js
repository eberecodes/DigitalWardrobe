import React from 'react';
import { Navbar, Nav, Form, Button } from 'react-bootstrap';
import { withRouter, Link } from 'react-router-dom';
import "./Navigation.css";
import logo from './logo.png';

const Navigation = (props) => {
    console.log(props);
    return (
        <Navbar>
          <Nav className="mr-auto">
            <Nav.Link href="/">
                   <Navbar.Brand>
                         <img
                            src={logo}
                            width="70"
                            height="90"
                            className="d-inline-block align-top"
                            alt="Logo"
                         />
                   </Navbar.Brand>
             </Nav.Link>
            <Nav.Link href="/AddItem">Add Item</Nav.Link>
          </Nav>
        </Navbar>
    )
}

export default withRouter(Navigation);