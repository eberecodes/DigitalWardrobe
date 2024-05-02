import { React, useState, useContext } from "react";
import './Wardrobe.css';
import { ImageContext } from './../ImageContext';

export const Wardrobe = () => {
const { imageData, loading } = useContext(ImageContext);
    return (
        <div className="wardrobe">
          {imageData.map((image, index) => (
            <img
              key={index}
              className="wardrobe_item"
              src={image.imageUrl}
            />
          ))}
        </div>
      );
};
