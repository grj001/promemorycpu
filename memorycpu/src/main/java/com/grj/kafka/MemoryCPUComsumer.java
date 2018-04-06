package com.grj.kafka;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator 拉取kafka中的数据
 */
public class MemoryCPUComsumer {
	private static KafkaConsumer<String, String> consumer;
	private static Object lock1 = new Object();
	private static Object lock2 = new Object();

	public static Logger logger = LoggerFactory.getLogger(MemoryCPUComsumer.class);

	/**
	 * kafka consumer 参数设置
	 */
	private MemoryCPUComsumer() {
	}

	/**
	 * 初始化 MemoryCPUComsumer
	 */
	public static MemoryCPUComsumer getInstance() {
		synchronized (lock1) {
			if (consumer == null) {
				Properties properties = null;
				properties = new Properties();
				properties.put("bootstrap.servers", "hadoop01:9092");
				properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
				properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
				// auto.offset.reset值含义解释
				// earliest
				// 客户端读取30条信息，且各分区的offset从0开始消费。
				// latest
				// 客户端读取0条信息。
				// none
				// 抛出NoOffsetForPartitionException异常。
				properties.put("auto.offset.reset", "latest");
				// 是否自动提交已拉取消息的offset。提交offset即视为该消息已经成功被消费
				properties.put("enable.auto.commit", "true");
				// 一个消费者只能同时消费一个分区的数据
				properties.put("group.id", "memorycpu");

				consumer = new KafkaConsumer<String, String>(properties);
			}
			return new MemoryCPUComsumer();
		}
	}

	// 订阅
	public String subscribeTopic() {
		synchronized (lock2) {
			ArrayList<String> topics = new ArrayList<String>();
			topics.add("grj");
			consumer.subscribe(topics);
			// 从kafka中拉取数据
			ConsumerRecords<String, String> records = consumer.poll(1);
			for (ConsumerRecord<String, String> record : records) {
				// System.out.println("接收到的消息partition: " +
				// record.partition());
				// System.out.println("接收到的消息offset: " + record.offset());
				// System.out.println("接收到的消息key: " + record.key());
				return record.value();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		MemoryCPUComsumer comsumer = new MemoryCPUComsumer();
		comsumer.subscribeTopic();
	}
}
