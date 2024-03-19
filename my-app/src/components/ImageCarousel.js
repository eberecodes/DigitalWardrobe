import { React, useState } from "react";
import { motion, AnimatePresence } from "framer-motion";

const ImageCarousel = ({ images }) => {
    const [currentIndex, setCurrentIndex] = useState(0);

    const handleNext = () => {
      setCurrentIndex((prevIndex) =>
        prevIndex + 1 === images.length ? 0 : prevIndex + 1
      );
    };

    const handlePrevious = () => {
       setCurrentIndex((prevIndex) =>
         prevIndex - 1 < 0 ? images.length - 1 : prevIndex - 1
       );
    };


    return (
        <div className="carousel" style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                <div className="left" onClick={handlePrevious} style={{ cursor: 'pointer', zIndex: 10 }}>
                      <svg xmlns="http://www.w3.org/2000/svg" height="50" viewBox="0 96 960 960" width="50">
                        <path d="M400 976 0 576l400-400 56 57-343 343 343 343-56 57Z" />
                      </svg>
                </div>
                {images[currentIndex] ? (
                      <img
                        key={currentIndex}
                        src={images[currentIndex].imageUrl}
                        alt="carousel"
                        style={{ maxWidth: '500px', height: 'auto', margin: '0 20px' }}
                      />
                    ) : (
                      <div>Loading...</div> // TODO: change this to a loading image
                    )}
                <div className="slide_direction">

                <div className="right" onClick={handleNext} style={{ cursor: 'pointer', zIndex: 10 }}>
                      <svg xmlns="http://www.w3.org/2000/svg" height="50" viewBox="0 96 960 960" width="50">
                        <path d="m304 974-56-57 343-343-343-343 56-57 400 400-400 400Z" />
                      </svg>
                    </div>
              </div>
            </div>
    );
};


export default ImageCarousel;