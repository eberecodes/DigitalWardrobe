import React, { useContext } from "react";
import { Button } from 'react-bootstrap';
import history from './history';
import ImageCarousel from './components/ImageCarousel';
import { ImageContext } from './ImageContext';

export default function Home() {
  const { imageData, loading } = useContext(ImageContext);

  return (
    <div className="Home">
      <center><h1>Welcome to Your Digital Wardrobe</h1></center>
      <main>
        {loading ? (
          <p>Loading Images...</p>
        ) : (
          <ImageCarousel images={imageData} />
        )}
      </main>
    </div>
  );
}
