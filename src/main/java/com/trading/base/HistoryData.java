package com.trading.base;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryData extends BaseResponse {
	public static class Payload {
		public static class Hits {
			public static class Hit {
				public static class Source {
					public long datetime;
					public double buy, sell;
				}

				public Source _source;
			}

			public List<Hit> hits;
		}

		public static class Aggregations {
			public static class All {
				public static class Buckets {
					public static class Value {
						public double value;
					}

					public long key;
					public Value volume, volumeValue, sell, buy;
				}

				public List<Buckets> buckets;
			}

			public All all;
		}

		public Hits hits;
		public Aggregations aggregations;
		public String message;
	}

	public Payload payload;
}
