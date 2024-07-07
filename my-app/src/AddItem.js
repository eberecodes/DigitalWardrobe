import React, { useState } from "react";
import ProductDetail from './ProductDetail';

const AddItem = () => {
  const [selectedFiles, setSelectedFiles] = useState([]);
  const [title, setTitle] = useState('');
  const [brand, setBrand] = useState('');
  const [size, setSize] = useState('');
  const [category, setCategory] = useState('');
  const [showPreview, setShowPreview] = useState(false);

  const handleFileSelect = (event) => {
    if (event.target.files && event.target.files.length > 0) {
       const filesArray = Array.from(event.target.files);
       setSelectedFiles([...selectedFiles, ...filesArray]);
    } else {
      console.error("No files selected");
    }
  };

  const handlePreview = () => {
      setShowPreview(true);
  };

  const retrieveItem= async (title) => {
  try{
    const response = await fetch(`http://localhost:8080/api/v1/item/items/getItem?title=${title}`, {
        method: "GET"
    });
    if (response.ok) {
        const item = await response.json();
        console.log("Item retrieved successfully:", item);
        // do something with retrieved item, use this for interim search capabilities, use product details
    } else {
        console.error("Failed to retrieve item:", response.statusText);
    }
  } catch (error) {
        console.error("Error retrieving item:", error.message);
    }
  };

  const handleRetrieve = () => {
    const itemTitle = prompt("Enter item title:");
    if (itemTitle) {
        retrieveItem(itemTitle);
      } else {
        console.error("Item title cannot be empty");
      }
  };

  return (
    <div>
      <label>Title:
      <input type="text" onChange={(e) => setTitle(e.target.value)} /></label>
      <label>Size:
      <input type="text" onChange={(e) => setSize(e.target.value)} /></label>
      <label>Brand:
      <input type="text" onChange={(e) => setBrand(e.target.value)} /></label>
      <label>Category:
      <input type="text" onChange={(e) => setCategory(e.target.value)} /></label>
      <input type="file" accept="image/*" onChange={handleFileSelect} multiple />
      <button onClick={handlePreview}>Preview</button>
      <button onClick={handleRetrieve}>Retrieve</button>

      {showPreview && (
              <ProductDetail
                selectedFiles={selectedFiles}
                title={title}
                brand={brand}
                category={category}
                size={size}
              />
            )}
    </div>
  );
};

export default AddItem;