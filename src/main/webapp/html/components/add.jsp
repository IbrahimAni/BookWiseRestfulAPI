<!-- Data Input Section -->
			<div class="data-input-section">
				<div class="bg-white shadow rounded p-4">
					<h2 class="text-xl font-semibold mb-4">Add Book</h2>
					<form id="book-form" action="add-book" method="POST">
						<div class="mb-4">
							<label for="book-id" class="block text-sm font-semibold mb-1">
							</label> <input type="text" class="form-input w-full p-2 border rounded"
								id="book-id" name="id" value="00" hidden="true">
						</div>
					
						<div class="mb-4">
							<label for="book-title" class="block text-sm font-semibold mb-1">Book
								Title</label> <input type="text"
								class="form-input w-full p-2 border rounded" id="book-title"
								placeholder="Enter book title" name="title"
								>
						</div>
						<div class="mb-4">
							<label for="book-author" class="block text-sm font-semibold mb-1">Author</label>
							<input type="text" class="form-input w-full p-2 border rounded"
								id="book-author" placeholder="Enter author name" name="author"
								>
						</div>
						<div class="mb-4">
							<label for="book-date" class="block text-sm font-semibold mb-1">Date</label>							
							<input type="date" class="form-input w-full p-2 border rounded"
								id="book-date" name="date">
						</div>
						<div class="mb-4">
							<label for="book-genres" class="block text-sm font-semibold mb-1">Genres</label>
							<input type="text" class="form-input w-full p-2 border rounded"
								id="book-genres" placeholder="Enter genres" name="genres"
								>
						</div>
						<div class="mb-4">
							<label for="book-characters"
								class="block text-sm font-semibold mb-1">Characters</label> <input
								type="text" class="form-input w-full p-2 border rounded"
								id="book-characters" placeholder="Enter characters"
								name="characters">
						</div>
						<div class="mb-4">
							<label for="book-synopsis"
								class="block text-sm font-semibold mb-1">Synopsis</label>
							<textarea class="form-textarea w-full p-2 border rounded"
								id="book-synopsis" placeholder="Enter synopsis" name="synopsis"></textarea>
						</div>
						<button type="submit" onclick="submitBook()"
							class="bg-blue-500 text-white py-2 px-4 rounded">Submit</button>
					</form>
				</div>
			</div>