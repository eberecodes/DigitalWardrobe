import React, { Component } from "react";
import { Button } from 'react-bootstrap';
import history from './history';

export default class Home extends Component {
  render() {
    return (
      <div className="Home">
        <div className="lander">
          <center><h1>Welcome to Your DigitalWardrobe</h1></center>
          <form>
            <Button variant="btn btn-success" onClick={() => history.push('/AddItem')}>Add New Item</Button>
          </form>
        </div>
      </div>
    );
  }
}
