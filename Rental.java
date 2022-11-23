import java.util.Date;
//conflict test
public class Rental {

	public enum RENTAL_STATUS{  // Enum 추가
		RENTED,RETURNED
	}

	private Video video ;
	private RENTAL_STATUS status ; // 0 for Rented, 1 for Returned
	private Date rentDate ;
	private Date returnDate ;

	public Rental(Video video) {
		this.video = video ;
		status = RENTAL_STATUS.RENTED ;
		rentDate = new Date() ;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	private RENTAL_STATUS getStatus() {
		return status;
	}

	public void returnVideo() {
		if ( status == RENTAL_STATUS.RETURNED ) {
			// 불필요한 코드 제거
			returnDate = new Date() ;
		}
	}
	public Date getRentDate() {
		return rentDate;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public int getDaysRentedLimit() {
		int limit = 0 ;
		int daysRented = getDaysRented();
		if ( daysRented <= 2) return limit ;

		return video.getVideoType().getLimit();
	}

	public int getDaysRented(){  // 중복코드 제거
		int daysRented;
		if (getStatus() == RENTAL_STATUS.RENTED) { // returned Video
			long diff = returnDate.getTime() - returnDate.getTime();
			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		} else { // not yet returned
			long diff = new Date().getTime() - returnDate.getTime();
			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		}
		return daysRented;
	}

	public int getPoint(){
		int point = 0 ;
		int daysRented = getDaysRented();

		if(video.getPriceCode() == Video.NEW_RELEASE)
			point++;
		point++;

		if ( daysRented > getDaysRentedLimit() )
			point -= Math.min(point, video.getLateReturnPointPenalty()) ;
		return point;
	}

	public double getCharge(){
		double eachCharge = 0;
		int daysRented = getDaysRented();
		switch (video.getPriceCode()) {
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
}
