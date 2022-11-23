import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {
	private String name;

	private List<Rental> rentals = new ArrayList<Rental>();

	public Customer(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}

	public void addRental(Rental rental) {
		rentals.add(rental);

	}

	public String getReport() {
		String result = "Customer Report for " + getName() + "\n";

		List<Rental> rentals = getRentals();

		double totalCharge = 0;
		int totalPoint = 0;

		for (Rental each : rentals) {

			int daysRented = getDaysRented(each);
			double eachCharge = getEachCharge(each, 0, daysRented);
			int eachPoint = getEachPoint(each, 0, daysRented);

			result += "\t" + each.getVideo().getTitle() + "\tDays rented: " + daysRented + "\tCharge: " + eachCharge
					+ "\tPoint: " + eachPoint + "\n";

			totalCharge += eachCharge;

			totalPoint += eachPoint ;
		}

		result += "Total charge: " + totalCharge + "\tTotal Point:" + totalPoint + "\n";


		if ( totalPoint >= 10 ) {
			System.out.println("Congrat! You earned one free coupon");
		}
		if ( totalPoint >= 30 ) {
			System.out.println("Congrat! You earned two free coupon");
		}
		return result ;
	}

	private static int getEachPoint(Rental each, int eachPoint, int daysRented) {
		eachPoint++;

		if ((each.getVideo().getPriceCode() == Video.NEW_RELEASE) )
			eachPoint++;

		if ( daysRented > each.getDaysRentedLimit() )
			eachPoint -= Math.min(eachPoint, each.getVideo().getLateReturnPointPenalty()) ;
		return eachPoint;
	}

	private static double getEachCharge(Rental each, double eachCharge, int daysRented) {
		switch (each.getVideo().getPriceCode()) {
		case Video.REGULAR:
			eachCharge += 2;
			if (daysRented > 2)
				eachCharge += (daysRented - 2) * 1.5;
			break;
		case Video.NEW_RELEASE:
			eachCharge = daysRented * 3;
			break;
		}
		return eachCharge;
	}

	private static int getDaysRented(Rental each) {
		int daysRented;
		if (each.getStatus() == 1) { // returned Video
			long diff = each.getReturnDate().getTime() - each.getRentDate().getTime();
			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		} else { // not yet returned
			long diff = new Date().getTime() - each.getRentDate().getTime();
			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		}
		return daysRented;
	}
}
