import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import type { FeatureFlag } from "../types/FeatureFlag.ts";
import axios from "axios";

export default function FeatureFlagTable() {
	const {
		isPending,
		error,
		data: flags,
	} = useQuery({
		queryKey: ["featureFlags"],
		queryFn: () => axios.get("http://localhost:8080/api/feature-flags").then((res) => res.data),
	});

	const queryClient = useQueryClient();

	const featureFlagMutation = useMutation({
		mutationFn: async (flagId: string) => {
			const res = await axios.patch(`http://localhost:8080/api/feature-flags/${flagId}/toggle`);
			return res.data;
		},
		onSuccess: async () => {
			await queryClient.invalidateQueries({queryKey: ["featureFlags"]});
		},
		onError: (err) => console.error(err),
	})

	const toggleFlag = (flagId: string) => {
		featureFlagMutation.mutate(flagId)
	}

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
									<button
										onClick={() => toggleFlag(flag.id)}
										className={flag.isEnabled ? "status-on" : "status-off"}
										style={{ cursor: 'pointer', border: 'none', fontSize: '1rem' }}
									>
										{flag.isEnabled ? "ENABLED" : "DISABLED"}
									</button>
								</td>
							</tr>
						))}
					</tbody>
				</table>
			)}
		</div>
	);
}
