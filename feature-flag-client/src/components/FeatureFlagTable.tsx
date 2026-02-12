import { useQuery } from "@tanstack/react-query";
import type { FeatureFlag } from "../types/FeatureFlag.ts";

export default function FeatureFlagTable() {
	const {
		isPending,
		error,
		data: flags,
	} = useQuery({
		queryKey: ["featureFlags"],
		queryFn: () =>
			fetch("http://localhost:8080/api/feature-flags").then((res) =>
				res.json(),
			),
	});

	if (isPending) return <div>Loading flags...</div>;
	if (error) return <div style={{ color: "red" }}>{error.message}</div>;

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
						{flags.map((flag: FeatureFlag) => (
							<tr key={flag.id}>
								<td>
									<code>{flag.key}</code>
								</td>
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
