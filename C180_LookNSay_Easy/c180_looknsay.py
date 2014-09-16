def look_and_say(N):
	look = '1'
	for i in range(N):
		final = ''
		start = look[0]
		current = 0
		for j in look:
			if j == start:
				current += 1
			else:
				final += str(current) + str(start)
				start = j
				current = 1
		final += str(current) + str(start)
		look = final
	return look

for i in range(15):
	print look_and_say(i)
