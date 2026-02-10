import React from 'react';
import './App.css';
import type {FeatureFlag} from './types/FeatureFlag';

function App() {
    const [flags, setFlags] = React.useState<Array<FeatureFlag>>([]);
    const [loading, setLoading] = React.useState<boolean>(true);
    const [error, setError] = React.useState<string | null>(null);

    React.useEffect(() => {
        fetch('http://localhost:8080/api/feature-flags')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setFlags(data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Error fetching flags:", err);
                setError("Failed to load feature flags.");
                setLoading(false);
            });
    }, []);

    if (loading) return <div>Loading flags...</div>;
    if (error) return <div style={{ color: 'red' }}>{error}</div>;

    return (
        <div className="container">
            <h1>Feature Flags</h1>
            {flags.length === 0 ? (
                <p>No flags found.</p>
            ) : (
                <table className="flag-table">
                    <thead>
                    <tr>
                        <th>Name (Key)</th>
                        <th>Description</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    {flags.map((flag) => (
                        <tr key={flag.id}>
                            <td><code>{flag.key}</code></td>
                            <td>{flag.description}</td>
                            <td>
                  <span className={flag.isEnabled ? "status-on" : "status-off"}>
                    {flag.isEnabled ? "ENABLED" : "DISABLED"}
                  </span>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default App;