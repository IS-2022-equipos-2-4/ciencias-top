package com.unam.cienciastop.auth;

public class JwtConfig {

	public static final String LLAVE_SECRETA = "alguna.clave.secreta.12345678";
	
	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\r\n"
			+ "MIIEogIBAAKCAQEAujWNYrYwu+a8WnhLHJhLd9cGnB4O1hCcgBnDu+YrCGVugFuB\r\n"
			+ "iqnpIW0X84TKS1/TGgXfvkiQyyniT94AShXpQg+n9D3x7XllUQ5FD/f5fShkAMBI\r\n"
			+ "etCgS/MZU+6rV3cIq7gDMxqCRFnQESTyTmlL8qC5ubI0PCTrD9hSGwdIUDsJJPXT\r\n"
			+ "FzDARfv3jRrqi6Gke20t31JdHJDlzp4vxjyGB2vrPTaJQSfAt1/r1GBX8B/Xjir3\r\n"
			+ "9dQvwctZOlLQKsKtyQGh/7QXQTZM6OKNTXLc3Y5veUeX+VvJFpkxWQKDNQNzwU9f\r\n"
			+ "pepK3vhm0f1wQ7TaVx45D4mPzMT0PVqu0JKstQIDAQABAoIBABGSxkIfoZ/KGdKL\r\n"
			+ "8cLEGLM5ptUK/Sl0reBlDhTolv0W3YPdnu8gM/FEo3MHADR22axmC4SgwpF55XLW\r\n"
			+ "J77WLIpggeBKDE/2Gfq5Vwk3xOLr8ewkOXLlx4sDaUuDhcYeZMIX/q1O/iZHN5y2\r\n"
			+ "mOuPgmxmgMCmsclPTqPTLYSBfnCbyEeyMv7n6ZlWd30PjUHj87kWH+yJT8kkoLlK\r\n"
			+ "9EZTDmaGYeNhPKEfIDCFsYjA2d1y2T4qUiEv382AvL6Q6r72zU+gbfF4vdHoJXS+\r\n"
			+ "g1axC4bMaSQo88N4wh/p8/7ExOnj4kNh9QqWS+fj0pcqhEYaaB2IOsGaGYyAGlN4\r\n"
			+ "botPUwECgYEA8a8HGi5gQ2hPFZhWDmabCbNwPjOdRHNKb0qg7wbG3JFztLChcFpR\r\n"
			+ "G7Qpg4bdK5pfF4K6cq8Nsn3YcH6Me1nMHGCsyMuYto3mCVMPUCmBTGu67DeQ/mJK\r\n"
			+ "BYlKTHWfTHMs4JxQeFjUUeQjGXNOSCPjHEfOBumGWlGQhWby57NxU/UCgYEAxT1K\r\n"
			+ "X6A4JmQRQv6zdniq0Qrhnjp0Dv+ZP9eR52puDrge2svc/furAZOBKAFBVjWv6sJS\r\n"
			+ "Fh8/TvmsCdVToBxFZ8rZ+lyEX45ablicJQrtilNIXEjeteI2fnGILqhVR0k8GWd1\r\n"
			+ "rTUwhlymbaemseMyDInlxOJ4IXb/Vg/yuZb9PcECgYAYwIxBqi5BuRUgaRGnN+r4\r\n"
			+ "+1+rfy3ELOqaq1IAKPmE7YCkKNLf5uAXrkPyLIXKSW0jw0fFJxiIB0t7efp5R6EX\r\n"
			+ "5u7LQc7KI45DtjYDR7zuTro+JxK6euaJ2p25ZvKBqeoWtuum57IXEP5nRN/nk4VX\r\n"
			+ "CCOZoaEY7vGWOAz4nfAlYQKBgELKs5HYXstZXaMTP1CiIi4yAh8j6kNd+NZuuKDm\r\n"
			+ "IKjpXsF3AbocbFdXTQhwDGEWQyE7drK5GCY1VDXsQKpaiIJSZihrBgSZ3zO42/+L\r\n"
			+ "SM9rzng50PlExnFhHt218VL3kNuFdosWFP2bCFzfxgo9RqiKVf2rN0UV1/eb5PVG\r\n"
			+ "FTHBAoGARUYRLdYqvPLHAASUSnsAtN3qcec8tvgCh07Qaj1+AZrsO3c2Y8brLgV8\r\n"
			+ "9H5an1+re2velzSrq8JpYJ5YOzzTy7yssEmRLuqIOnpUZ31v2j+4PpmN/qO3H2WU\r\n"
			+ "AlhpXZUJ84t3WVItHKvv9wpv5DsQGQLkr09Q5FDV1JV5ZBrEuxU=\r\n"
			+ "-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\r\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAujWNYrYwu+a8WnhLHJhL\r\n"
			+ "d9cGnB4O1hCcgBnDu+YrCGVugFuBiqnpIW0X84TKS1/TGgXfvkiQyyniT94AShXp\r\n"
			+ "Qg+n9D3x7XllUQ5FD/f5fShkAMBIetCgS/MZU+6rV3cIq7gDMxqCRFnQESTyTmlL\r\n"
			+ "8qC5ubI0PCTrD9hSGwdIUDsJJPXTFzDARfv3jRrqi6Gke20t31JdHJDlzp4vxjyG\r\n"
			+ "B2vrPTaJQSfAt1/r1GBX8B/Xjir39dQvwctZOlLQKsKtyQGh/7QXQTZM6OKNTXLc\r\n"
			+ "3Y5veUeX+VvJFpkxWQKDNQNzwU9fpepK3vhm0f1wQ7TaVx45D4mPzMT0PVqu0JKs\r\n"
			+ "tQIDAQAB\r\n"
			+ "-----END PUBLIC KEY-----";
	
	
}
