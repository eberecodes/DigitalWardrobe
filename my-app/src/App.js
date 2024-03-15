import React, { useState } from "react";

const App = () => {
  const [selectedFiles, setSelectedFiles] = useState([]);
  const [title, setTitle] = useState('');
  const [brand, setBrand] = useState('');
  const [size, setSize] = useState('');
  const [category, setCategory] = useState('');

  const handleFileSelect = (event) => {
    if (event.target.files && event.target.files.length > 0) {
       const filesArray = Array.from(event.target.files);
       setSelectedFiles([...selectedFiles, ...filesArray]);
    } else {
      console.error("No files selected");
    }
  };

  const uploadImage = async () => {
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
        } else {
            console.error("Failed to create item, response status: ", response.status);
        }
    } catch (error) {
        console.error("Error occurred: ", error);
    }
    };

  const retrieveItem= async (title) => {
  try{
    const response = await fetch(`http://localhost:8080/api/v1/item/items/getItem?title=${title}`, {
        method: "GET"
    });
    if (response.ok) {
        const item = await response.json();
        console.log("Item retrieved successfully:", item);
        // do something with retrieved item
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
      <button onClick={uploadImage}>Upload</button>
      <button onClick={handleRetrieve}>Retrieve</button>
    </div>
  );
};

export default App;
