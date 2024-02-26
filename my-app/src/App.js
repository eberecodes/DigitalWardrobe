import React, { useState } from "react";

const App = () => {
  const [selectedFile, setSelectedFile] = useState(null);
  const [title, setTitle] = useState('');

  const handleFileSelect = (event) => {
    if (event.target.files && event.target.files.length > 0) {
      setSelectedFile(event.target.files[0]);
    } else {
      console.error("No files selected");
    }
  };

  const uploadImage = async () => {
    const formData = new FormData();
    formData.append("title", title);
    formData.append("image", selectedFile);

    try {
      const response = await fetch(`http://localhost:8080/api/v1/image/images/add`, {
        method: "POST",
        body: formData,
      });

      if (response.ok) {
        console.log("Image uploaded successfully");
        // Handle success response here
      } else {
        console.error("Failed to upload image");
        // Handle error response here
      }
    } catch (error) {
      console.error("Error uploading image:", error);
      // Handle network error here
    }
  };
  const retrieveImage = async (id) => {
    const response = await fetch(`http://localhost:8080/api/v1/image/images/${id}`);
    if (response.ok) {
       const blob = await response.blob();
       const url = URL.createObjectURL(blob);
       const img = new Image();
       img.src = url;
       document.body.appendChild(img);
     } else {
        console.error("Error retrieving image");
     }
    };
  const handleRetrieve = () => {
    const id = prompt("Enter image ID:");
    retrieveImage(id);
  };

  return (
    <div>
      <label>Title:
      <input type="text" onChange={(e) => setTitle(e.target.value)} /></label>
      <input type="file" accept="image/*" onChange={handleFileSelect} />
      <button onClick={uploadImage}>Upload</button>
      <button onClick={handleRetrieve}>Retrieve</button>
    </div>
  );
};

export default App;
