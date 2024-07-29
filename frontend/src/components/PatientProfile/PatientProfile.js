import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import ProfileItem from '../ProfileItem/ProfileItem';
import './PatientProfile.css';

const PatientProfile = () => {
  const { id } = useParams();
  const [patientData, setPatientData] = useState(null);
  const [errorMessage, setErrorMessage] = useState(null);

  // State for managing molecule search
  const [moleculeName, setMoleculeName] = useState('');
  const [moleculeResults, setMoleculeResults] = useState([]);
  const [isSearching, setIsSearching] = useState(false);
  const [infoMessage, setInfoMessage] = useState(''); // Added to manage info messages

  useEffect(() => {
    // Fetch patient details
    fetch(`http://localhost:8080/api/v1/patients/${id}`, {
      method: "GET",
      mode: "cors",
    })
      .then(response => response.json())
      .then(data => setPatientData(data))
      .catch(error => {
        console.error(error);
        setErrorMessage({ title: "Error fetching patient data", details: error.message });
      });
  }, [id]);

  // Handle search input changes
  const handleSearchChange = (e) => {
    setMoleculeName(e.target.value);

    if (e.target.value.trim() !== '') {
      setIsSearching(true);
      fetch(`http://localhost:8080/api/v1/molecules/search?name=${e.target.value}`, {
        method: 'GET',
        mode: 'cors',
      })
        .then(response => response.json())
        .then(data => {
          if (Array.isArray(data)) {
            setMoleculeResults(data);
          } else {
            setMoleculeResults([]);
          }
        })
        .catch(error => {
          console.error('Error fetching molecules:', error);
          setMoleculeResults([]);
        });
    } else {
      setIsSearching(false);
      setMoleculeResults([]);
    }
  };

  // Handle adding allergy
  const handleAddAllergy = (molecule) => {
    fetch(`http://localhost:8080/api/v1/patient-allergies/${id}/allergies`, {
      method: 'POST',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(molecule.id), // Pass molecule ID directly
    })
      .then(response => {
        //console.log("PRINTING RESPONSE :: " + response.status);
        if (response.status === 200) {
          setInfoMessage('Allergy added successfully');
        } else if (response.status === 409) {
          setInfoMessage('Allergy already exists'); // Show message if allergy already exists
        } else {
          setInfoMessage('Error adding allergy'); // Show error message
        }
        return response.text(); // Return response as text for better debugging
      })
      .then(text => {
        try {
          const data = JSON.parse(text); // Try parsing the response text as JSON
          if (data) {
            setMoleculeName('');
            setMoleculeResults([]);
            setIsSearching(false);
            // No need to set success message here, it's already set above
          }
        } catch (e) {
          //console.error('Error parsing response as JSON:', e, text);
          setInfoMessage('Error adding allergy: Invalid response from server'); // Show error message
        }
      })
      .catch(error => {
        //console.error('######### Error adding allergy:', error);
        setInfoMessage('Error adding allergy'); // Show error message
      });
  };

  if (errorMessage) {
    return <div className="container">
      <h1>{errorMessage.title}</h1>
      <p>{errorMessage.details}</p>
    </div>
  }
  if (!patientData) {
    return <div className="container">Loading...</div>;
  }

  return (
    <div className="container">
      <h1>Patient Profile</h1>
      <div className="patient-profile">
        <ProfileItem label="ID" value={patientData.id} />
        <ProfileItem label="First Name" value={patientData.first_name} />
        <ProfileItem label="Last Name" value={patientData.last_name} />
        <ProfileItem label="Birth Date" value={patientData.birth_date} />
        <ProfileItem label="Height" value={patientData.height} />
        <ProfileItem label="Weight" value={patientData.weight} />
      </div>

      {/* Search and Add Allergy Feature */}
      <div className="search-container">
        <h3 className="allergy-heading">Add Allergy</h3>
        <input
          type="text"
          value={moleculeName}
          onChange={handleSearchChange}
          placeholder="Enter molecule name"
          className="search-input"
        />
        {isSearching && moleculeResults.length > 0 && (
          <ul className="dropdown">
            {moleculeResults.map((molecule, index) => (
              <li key={index} className="dropdown-item">
                {molecule.name}
                <button className="add-button" onClick={() => handleAddAllergy(molecule)}>Add</button>
              </li>
            ))}
          </ul>
        )}
        {infoMessage && ( // Display info message
          <div className="info-message">
            {infoMessage}
          </div>
        )}
      </div>
    </div>
  );
};

export default PatientProfile;
