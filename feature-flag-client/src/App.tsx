import "./App.css";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import FeatureFlagTable from "./components/FeatureFlagTable.tsx";

const queryClient = new QueryClient();

export default function App() {
	return (
		<QueryClientProvider client={queryClient}>
			<FeatureFlagTable />
		</QueryClientProvider>
	);
}
