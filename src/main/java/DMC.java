import java.util.*;

public class DMC {
	private DatasetCar dataset;
	private double[][] centroids = {{0,0,0,0,0,0}, {0,0,0,0,0,0}, {0,0,0,0,0,0}, {0,0,0,0,0,0}};

	public DMC() {
		this.dataset = new DatasetCar();
		this.getCentroids();
	}

	public DMC(DatasetCar data) {
		this.dataset = data;
		this.getCentroids();
	}

	public String classify(int[] ints) {
		double[] vector = new double[]{ints[0],ints[1],ints[2],ints[3],ints[4],ints[5]};
		int index = 0;
		double minorDist = -1;


		System.out.print("Entry: ");
		for(int i=0; i<6; i++) System.out.print(vector[i] + ", ");
		System.out.println("");

		for(int i=0; i<4; i++) {
			double dist = this.getDistance(vector, this.centroids[i]);
			if(dist < minorDist || minorDist == -1) {
				minorDist = dist;
				index = i;
			}
		}

		return CarClass.get(index).toString();
	}

	public void logCentroids() {
		System.out.println("Centroids:");
		for(int i=0; i<4; i++) {
			System.out.printf(java.util.Locale.US,"{%.2f", this.centroids[0][0]);
			for(int j=1; j<6; j++) System.out.printf(java.util.Locale.US, ", %.2f", this.centroids[i][j]);
			System.out.println("}");
		}
	}

	public void logCounter(int[] counter) {
		System.out.print("Counter:\n{" + counter[0]);
		for(int i=1; i<4; i++) {
			System.out.print(", "+counter[i]);
		}
		System.out.println("}");
	}

	public void getCentroids() {
		int[] counter = {0,0,0,0};

		for(Car car : this.dataset.getCars()) {
			int index = car.getCarClass().getValue();
			counter[index] += 1;
			int[] attr = car.getAttributes();
			for(int j=0; j < 6; j++) {
				this.centroids[index][j] += attr[j];
			}
		}

		for(int i=0; i<4; i++) {
			for(int j=0; j<6; j++) {
				this.centroids[i][j] /= counter[i];
			}
		}
		this.logCentroids();
	}

	private double getDistance(double[] vetA, double[] vetB) {
		try {
			if(vetA.length == vetB.length) {
				double diff = 0;
				double sum = 0;
				for(int i=0; i<vetA.length; i++) {
					diff = Math.abs(vetA[i]-vetB[i]);
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
