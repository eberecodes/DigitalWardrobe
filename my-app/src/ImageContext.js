import React, { createContext, useState, useEffect } from 'react';

const API_URL = 'http://localhost:8080/api/v1/item/items/all';

const ImageContext = createContext({ loading: true, imageData: [], setImageData: () => {} });

const ImageProvider = ({ children }) => {
  const [imageData, setImageData] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchImages = async () => {
    try {
      const response = await fetch(API_URL, {
        method: 'GET'
      });
      if (response.ok) {
        const items = await response.json();
        const images = items.map(item => ({
          id: item.id,
          imageUrl: `data:image/jpeg;base64,${item.images[0].image.data}`
        }));
        setImageData(images);
        setLoading(false);
        console.log('Images retrieved successfully:', images);
      } else {
        console.error('Failed to retrieve images:', response.statusText);
      }
    } catch (error) {
      console.error('Error retrieving images:', error.message);
    }
  };

  useEffect(() => {
    fetchImages();
  }, []);

  return (
    <ImageContext.Provider value={{ loading, imageData, setImageData }}>
      {children}
    </ImageContext.Provider>
  );
};

export { ImageContext, ImageProvider };
