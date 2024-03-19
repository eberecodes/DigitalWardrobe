import { React, Component, useState } from "react";
import { Button } from 'react-bootstrap';
import history from './history';
import ImageCarousel from './components/ImageCarousel';

export default class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
          images: []
        };
      }

    componentDidMount() {
       this.getAllItems();
    }

    getAllItems = async () => {
        try{
            const response = await fetch(`http://localhost:8080/api/v1/item/items/all`, {
                method: "GET"
            });
            if (response.ok) {
                const items = await response.json();
                console.log("Item retrieved successfully:", items);
                const images = items.map(item => ({
                  id: item.id,
                  imageUrl: `data:image/jpeg;base64,${item.images[0].image.data}`,
                }));
                this.setState({ images });
            } else {
                console.error("Failed to retrieve item:", response.statusText);
            }
          } catch (error) {
                console.error("Error retrieving item:", error.message);
            }
   };

  render() {
    const { images } = this.state;
    return (
      <div className="Home">
        <center><h1>Welcome to Your DigitalWardrobe</h1></center>
        <main>
                <ImageCarousel images={images} />
        </main>
        <form>
          <Button variant="btn btn-success" onClick={() => history.push('/AddItem')}>Add New Item</Button>
        </form>
      </div>
    );
  }
}
