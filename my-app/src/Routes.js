import React, { Component } from "react";
import { Router, Switch, Route } from "react-router-dom";

import Home from "./Home";
import AddItem from "./AddItem";
import { Wardrobe } from './components/Wardrobe';
import history from "./history";
import { ImageProvider } from './ImageContext';

export default class Routes extends Component {
    render() {
        return (
            <ImageProvider>
            <Router history={history}>
                <Switch>
                    <Route path="/" exact component={Home} />
                    <Route path="/AddItem" component={AddItem} />
                    <Route path="/Wardrobe" component={Wardrobe} />
                </Switch>
            </Router>
            </ImageProvider>
        )
    }
}