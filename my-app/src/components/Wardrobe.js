import React, { useState, useContext } from "react";
import './Wardrobe.css';
import { ImageContext } from './../ImageContext';

export const Wardrobe = () => {
    const { imageData, setImageData, loading } = useContext(ImageContext);
    const [selectedItem, setSelectedItem] = useState(null);
    const [showModal, setShowModal] = useState(false);

    const handleItemClick = (index) => {
        setSelectedItem(index);
        setShowModal(true);
    };

    const handleDelete = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/item/items/${imageData[selectedItem].id}`, {
                method: "DELETE"
            });
            if (response.ok) {
                console.log("Item deleted successfully");
                const updatedImageData = [...imageData];
                updatedImageData.splice(selectedItem, 1);
                setImageData(updatedImageData);
                setShowModal(false);
                setSelectedItem(null);
            } else {
                console.error("Failed to delete item:", response.statusText);
            }
        } catch (error) {
            console.error("Error deleting item:", error.message);
        }
    };

    const handleCancel = () => {
        setSelectedItem(null);
        setShowModal(false);
    };

    return (
        <div className="wardrobe">
            {imageData.map((image, index) => (
                <div key={index} className="wardrobe_item_container">
                    <img
                        className="wardrobe_item"
                        src={image.imageUrl}
                        onClick={() => handleItemClick(index)}
                    />
                    {selectedItem === index && showModal && (
                        <div className="modal">
                            <div className="modal-content">
                                <p>Do you want to delete this item?</p>
                                <button onClick={handleDelete}>Delete</button>
                                <button onClick={handleCancel}>Cancel</button>
                            </div>
                        </div>
                    )}
                </div>
            ))}
        </div>
    );
};
