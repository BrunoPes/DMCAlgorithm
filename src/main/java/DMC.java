import java.util.*;

public class DMC {
	private DatasetCar dataset;

	public DMC() {
		this.dataset = new DatasetCar();
		this.dataset.readDatasetFile();
	}

	public String classify(int[] vector) {
		int index = 0;
		int[][] centroids = this.getCentroids();
		double minorDist = -1;
		for(int i=0; i<4; i++) {
			double dist = this.getDistance(vector, centroids[i]);
			if(dist < minorDist || minorDist == -1) {
				minorDist = dist;
				index = i;
			}
		}

		return CarClass.get(index).toString();
	}

	public int[][] getCentroids() {
		int[][] centroids = {{0,0,0,0,0,0}, {0,0,0,0,0,0}, {0,0,0,0,0,0}, {0,0,0,0,0,0}};
		int[] counter = {0,0,0,0};

		for(Car car : this.dataset.getCars()) {
			int i = car.getCarClass().getValue();
			int[] attr = car.getAttributes();
			for(int j=0; j < 6; j++) {
				centroids[i][j] += attr[j];
				counter[i]++;
			}
		}

		for(int i=0; i<4; i++) {
			for(int j=0; j<6; j++) {
				centroids[i][j] /= counter[i];
			}
		}

		return centroids;
	}

	private double getDistance(int[] vetA, int[] vetB) {
		try {
			if(vetA.length == vetB.length) {
				double diff = 0;
				double sum = 0;
				for(int i=0; i<vetA.length; i++) {
					diff = Integer.valueOf(Math.abs(vetA[i]-vetB[i])).doubleValue();
					sum += Math.pow(diff, new Double("2"));
				}

				return Math.sqrt(sum);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return -1;
	}
}