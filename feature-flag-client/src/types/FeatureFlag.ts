export interface FeatureFlag {
	id: string;
	key: string;
	description: string;
	isEnabled: boolean;
	createdAt?: string;
}
