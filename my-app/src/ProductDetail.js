import React, { useState } from "react";
import { AiOutlineHeart } from "react-icons/ai";
import ReactImageGallery from "react-image-gallery";
import ReactDom from 'react-dom';
import "react-rater/lib/react-rater.css";
import history from './history';

const ProductDetail = ({
  selectedFiles,
  title,
  brand,
  category,
  size,
}) => {
  const submitItem = async () => {
      const formData = new FormData();
      formData.append("title", title);
      formData.append("brand", brand);
      formData.append("size", size);
      formData.append("category", category);
      selectedFiles.forEach((file, index) => {
            formData.append(`images`, file);
      });

      try {
          const response = await fetch(`http://localhost:8080/api/v1/item/items/createItem`, {
              method: "POST",
              body: formData
          });

          if (response.ok) {
              console.log("Item creation successful");
              alert('Item creation successful');
              history.push('/')
          } else {
              console.error("Failed to create item, response status: ", response.status);
          }
      } catch (error) {
          console.error("Error occurred: ", error);
      }
      };

  return (
    <section>
      <div>
        <ReactImageGallery
          showBullets={false}
          showFullscreenButton={true}
          showPlayButton={false}
          items={selectedFiles.map(file => ({ original: URL.createObjectURL(file) }))}
        />
      </div>

      <div>
        <h2 >{title}</h2>
        <p>
          <span>{brand}</span>
        </p>
        <p>
          <span>{category}</span>
        </p>
        <p>
          <span>{size}</span>
        </p>
        <div>
          <div>
            <button onClick={submitItem}>
              Confirm
            </button>
            <button>
              <AiOutlineHeart/>
              Wishlist
            </button>
          </div>
        </div>
      </div>
    </section>
  );
};

export default ProductDetail;